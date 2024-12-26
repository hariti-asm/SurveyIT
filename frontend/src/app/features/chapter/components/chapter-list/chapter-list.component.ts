import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Chapter } from '../../models/chapter.model';
import { ChapterService } from '../../services/chapter.service';
import { Subscription } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';

interface ChapterResponse {
  success: boolean;
  data: {
    data: Chapter[];
  };
}

@Component({
  selector: 'app-chapter-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './chapter-list.component.html',
  styleUrl: './chapter-list.component.scss'
})
export class ChapterListComponent implements OnInit, OnDestroy {
  chapters: Chapter[] = [];
  loading = false;
  error: string | null = null;

  private readonly chapterService = inject(ChapterService);
  private readonly route = inject(ActivatedRoute);
  private subscription?: Subscription;

  ngOnInit(): void {
    this.subscription = this.route.params.subscribe(params => {
      const editionId = Number(params['editionId']);
      if (editionId && !isNaN(editionId)) {
        this.loadChapters(editionId);
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  private loadChapters(editionId: number): void {
    this.loading = true;
    this.error = null;

    this.chapterService.getChapters(editionId).pipe(
      catchError(error => {
        console.error('Error loading chapters:', error);
        this.error = error.message || 'An error occurred while loading chapters';
        throw error;
      }),
      finalize(() => {
        this.loading = false;
      })
    ).subscribe({
      next: (response: ChapterResponse) => {
        if (response?.success) {
          this.chapters = response.data.data;
          console.log("the incoming chapters are ", response.data.data);
        } else {
          this.error = 'Failed to load chapters';
        }
      }
    });
  }
}
