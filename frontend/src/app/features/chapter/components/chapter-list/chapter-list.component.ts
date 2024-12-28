import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ChapterItemComponent } from '../chapter-item/chapter-item.component';
import { Subscription } from 'rxjs';
import {ChapterService} from '../../services/chapter.service';

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
  private subscription?: Subscription;

  protected readonly route = inject(ActivatedRoute);
  private readonly chapterService = inject(ChapterService);

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
  }

  toggleAnswers(question: any): void {
    question.showAnswers = !question.showAnswers;
  }

  calculatePercentage(count: number, answers: any[]): number {
    const total = answers.reduce((sum, answer) => sum + answer.selectionCount, 0);
    return total === 0 ? 0 : Number(((count / total) * 100).toFixed(1));
  }
}
