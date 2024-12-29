import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ChapterItemComponent } from '../chapter-item/chapter-item.component';
import { Subscription } from 'rxjs';
import { ChapterService } from '../../services/chapter.service';
import { QuestionService } from '../../../question/services/question.service';
import { Question } from '../../models/question.model';
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
  questions: Question[] = [];

  protected readonly route = inject(ActivatedRoute);
  private readonly chapterService = inject(ChapterService);
  private readonly questionService = inject(QuestionService);
  private subscription?: Subscription;

  ngOnInit(): void {
    this.loadChapters();
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

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
    question.showAnswers = !question.showAnswers;
  }

  calculatePercentage(selectionCount: number, totalCount: number): number {
    return totalCount === 0 ? 0 : (selectionCount / totalCount) * 100;
  }
}
