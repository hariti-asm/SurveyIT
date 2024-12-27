import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { catchError, finalize, Subscription } from 'rxjs';
import { Chapter } from '../../models/chapter.model';
import { ChapterService } from '../../services/chapter.service';
import { ApiResponseDTO } from '../../models/apiResponse.model';
import { ChapterItemComponent } from '../chapter-item/chapter-item.component';

@Component({
  selector: 'app-chapter-list',
  standalone: true,
  imports: [CommonModule, ChapterItemComponent],
  templateUrl: './chapter-list.component.html',
  styleUrls: ['./chapter-list.component.scss']
})
export class ChapterListComponent implements OnInit, OnDestroy {
  chapters: Chapter[] = [];
  loading = false;
  error: string | null = null;

  private readonly chapterService = inject(ChapterService);
  protected readonly route = inject(ActivatedRoute);
  private subscription?: Subscription;
  ngOnInit(): void {
    this.subscription = this.route.params.subscribe(params => {
      const editionId = Number(params['editionId']);
      if (editionId && !isNaN(editionId)) {
        this.loadChapters(editionId);
      } else {
        this.error = 'Invalid Survey Edition ID';
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  private loadChapters(surveyEditionId: number): void {
    this.loading = true;
    this.error = null;

    this.chapterService.getChaptersBySurveyEdition(surveyEditionId).pipe(
      catchError(error => {
        this.error = error.message || 'An error occurred while loading chapters';
        throw error;
      }),
      finalize(() => this.loading = false)
    ).subscribe({
      next: (response: ApiResponseDTO<Chapter[]>) => {
        if (response?.success && response.data) {
          this.chapters = response.data;
          this.logChapters();
        } else {
          this.error = 'Failed to load chapters';
        }
      },
      error: (error) => {
        this.error = error.message || 'An error occurred while loading chapters';
      }
    });
  }

  private logChapters(): void {
    this.chapters.forEach((chapter, index) => {
      console.log(`
        Chapter ${index + 1}:
        ID: ${chapter.id}
        Title: ${chapter.title}
        Parent Chapter ID: ${chapter.parentChapterId || 'None'}
        Survey Edition ID: ${chapter.surveyEditionId}
        Questions Count: ${chapter.questions?.length || 0}
        SubChapters: ${chapter.subChapters?.length || 0}
      `);
    });
  }
}
