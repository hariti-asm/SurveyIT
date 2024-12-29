import {Component, OnInit, OnDestroy, inject, ElementRef, ViewChild} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ChapterItemComponent } from '../chapter-item/chapter-item.component';
import { Subscription } from 'rxjs';
import { ChapterService } from '../../services/chapter.service';
import { QuestionService } from '../../../question/services/question.service';
import { Question } from '../../models/question.model';
import {AnswerService} from '../../../answer/services/answer.service';
@Component({
  selector: 'app-chapter-list',
  standalone: true,
  imports: [CommonModule, ChapterItemComponent],
  templateUrl: './chapter-list.component.html',
  styleUrls: ['./chapter-list.component.scss']
})
export class ChapterListComponent implements OnInit, OnDestroy {
  chapters: any[] = [];
  loading = false;
  error: string | null = null;
  selectedSubChapter: any = null;
  selectedSubChapterId: number | null = null;
  editingQuestion: Question | null = null;
  newQuestion: Question | null = null;
  @ViewChild('newQuestionInput') newQuestionInput!: ElementRef;

  protected readonly route = inject(ActivatedRoute);
  private readonly chapterService = inject(ChapterService);
  private readonly questionService = inject(QuestionService);
  private readonly answerService = inject(AnswerService);
  private subscription?: Subscription;


  ngOnInit(): void {
    this.loadChapters();
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
  getQuestionType(type: string): string {
    return type === 'MULTIPLE_CHOICE' ? 'Multiple Choice' : 'Single Choice';
  }

  isRequiredText(required: boolean): string {
    return required ? 'Yes' : 'No';
  }
  selectedQuestion: any = null;

  private loadChapters(): void {
    this.loading = true;
    this.subscription = this.route.params.subscribe(params => {
      const editionId = Number(params['editionId']);
      if (editionId && !isNaN(editionId)) {
        this.chapterService.getChaptersBySurveyEdition(editionId).subscribe({
          next: (response) => {
            if (response?.success && response.data) {
              this.chapters = response.data;
              this.loading = false;
            } else {
              this.error = 'Failed to load chapters';
              this.loading = false;
            }
          },
          error: (error) => {
            this.error = error.message || 'An error occurred while loading chapters';
            this.loading = false;
          }
        });
      } else {
        this.error = 'Invalid Survey Edition ID';
        this.loading = false;
      }
    });
  }

  onSubChapterSelect(subChapter: any): void {
    this.selectedSubChapter = subChapter;
    this.selectedSubChapterId = subChapter.id;
    this.loadQuestions(subChapter);
  }

  private loadQuestions(subChapter: any): void {
    const surveyId = Number(this.route.snapshot.paramMap.get('surveyId'));
    const chapterId = subChapter.parentChapterId;
    const subChapterId = subChapter.id;

    this.loading = true;
    this.questionService.getQuestionsBySubChapter(surveyId, chapterId, subChapterId)
      .subscribe({
        next: (response) => {
          if (response.success) {
            this.selectedSubChapter = {
              ...subChapter,
              questions: response.data.map(q => ({ ...q, showAnswers: false })) // Initialize showAnswers property
            };
            this.loading = false;
          } else {
            this.error = 'Failed to load questions';
            this.loading = false;
          }
        },
        error: (error) => {
          console.error('Error loading questions:', error);
          this.error = error.message || 'Failed to load questions';
          this.loading = false;
        }
      });
  }

  toggleAnswers(question: any): void {
    // If this question is already selected, hide it
    if (this.selectedQuestion === question) {
      this.selectedQuestion = null;
      question.showAnswers = false;
      return;
    }

    // Set new selected question
    this.selectedQuestion = question;
    question.showAnswers = true;

    // Only fetch answers if we haven't already
    if (!question.answers) {
      this.loading = true;
      this.answerService.getAnswersByQuestionId(question.id).subscribe({
        next: (response) => {
          if (response) {
            question.answers = response;
            // Calculate percentages for the answers
            const totalSelections = question.answers.reduce(
              (sum: any, answer: { selectionCount: any; }) => sum + (answer.selectionCount || 0),
              0
            );

            question.answers.forEach((answer: { percentage: number; selectionCount: any; }) => {
              answer.percentage = this.calculatePercentage(
                answer.selectionCount || 0,
                totalSelections
              );
            });
          } else {
            this.error = 'Failed to load answers';
            question.showAnswers = false;
            this.selectedQuestion = null;
          }
          this.loading = false;
        },
        error: (error) => {
          console.error('Error loading answers:', error);
          this.error = error.message || 'Failed to load answers';
          this.loading = false;
          question.showAnswers = false;
          this.selectedQuestion = null;
        }
      });
    }
  }

  private calculateAnswerPercentages(question: Question): void {
    const totalSelections = question.answers?.reduce(
      (sum, answer) => sum + (answer.selectionCount || 0),
      0
    ) || 0;

    if (question.answers) {
      question.answers.forEach(answer => {
        answer.selectionCount = this.calculatePercentage(
          answer.selectionCount || 0,
          totalSelections
        );
      });
    }
  }

  calculatePercentage(selectionCount: number, totalCount: number): number {
    return totalCount === 0 ? 0 : Math.round((selectionCount / totalCount) * 100);
  }

}
