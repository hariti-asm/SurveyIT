import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import {catchError, finalize, Subscription} from 'rxjs';
import {Chapter} from '../../models/chapter.model';
import {ChapterService} from '../../services/chapter.service';
import {ApiResponseDTO} from '../../models/apiResponse.model';
import {PaginatedResponse} from '../../../survey/models/pagination.model';
import {ChapterItemComponent} from '../chapter-item/chapter-item.component';




@Component({
  selector: 'app-chapter-list',
  standalone: true,
  imports: [CommonModule, ChapterItemComponent],
  templateUrl: './chapter-list.component.html',
  styleUrl: './chapter-list.component.scss'
})
export class ChapterListComponent implements OnInit, OnDestroy {
  chapters: any[] = [];
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
      next: (response: ApiResponseDTO<PaginatedResponse<Chapter>>) => {
        if (response?.success) {
          this.chapters = response.data.data.content;
          console.log("The incoming chapters are ", this.chapters);
        } else {
          this.error = 'Failed to load chapters';
        }
      },
      error: (error) => {
        this.error = error.message || 'An error occurred while loading chapters';
      }
    });
  }
}
