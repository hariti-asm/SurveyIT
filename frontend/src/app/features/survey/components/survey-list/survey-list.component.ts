import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Survey } from '../../models/survey.model';
import { SurveyItemComponent } from '../survey-item/survey-item.component';
import { forkJoin, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { SurveyService } from '../../services/Survey.service';
import {ApiResponse} from '../../../question/services/question.service';

interface PaginatedResponse<T> {
  content: T[];
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: any;
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  totalElements: number;
  totalPages: number;
  last: boolean;
  first: boolean;
  size: number;
  number: number;
  sort: {
    empty: boolean;
    unsorted: boolean;
    sorted: boolean;
  };
  numberOfElements: number;
  empty: boolean;
}

@Component({
  selector: 'app-survey-list',
  imports: [
    CommonModule,
    SurveyItemComponent
  ],
  templateUrl: './survey-list.component.html',
  standalone: true,
  styleUrl: './survey-list.component.scss'
})
export class SurveyListComponent implements OnInit {
  private readonly surveyService = inject(SurveyService);
  surveys: Survey[] = [];
  loading = false;
  error: string | null = null;

  // Pagination properties
  currentPage = 0;
  totalPages = 0;
  pageSize = 10;
  totalElements = 0;

  ngOnInit() {
    this.loading = true;
    this.loadSurveysWithEditions();
  }

  private loadSurveysWithEditions() {
    this.surveyService.getAllSurveys().subscribe({
      next: (response: ApiResponse<PaginatedResponse<Survey>>) => {
        console.log('Received initial surveys:', response);

        if (!response.success || !response.data || !response.data.content) {
          console.error('Invalid survey response:', response);
          this.error = 'Failed to load surveys: Invalid data format';
          this.loading = false;
          return;
        }

        const surveys = response.data.content;

        // Update pagination info
        this.currentPage = response.data.number;
        this.totalPages = response.data.totalPages;
        this.totalElements = response.data.totalElements;
        this.pageSize = response.data.size;

        if (surveys.length === 0) {
          this.surveys = [];
          this.loading = false;
          return;
        }

        const editionRequests = surveys.map(survey =>
          this.surveyService.getSurveyEditions(survey.id).pipe(
            map(editionsResponse => ({
              ...survey,
              surveyEditions: editionsResponse // Remove the ApiResponse wrapper since backend doesn't use it
            })),
            catchError(err => {
              console.error(`Error loading editions for survey ID ${survey.id}:`, err);
              return of({
                ...survey,
                surveyEditions: []
              });
            })
          )
        );

        forkJoin(editionRequests).subscribe({
          next: (surveysWithEditions) => {
            console.log('Surveys with editions loaded:', surveysWithEditions);
            this.surveys = surveysWithEditions;
            this.loading = false;
          },
          error: (error) => {
            console.error('Error loading survey editions:', error);
            this.error = 'Failed to load survey editions';
            this.loading = false;
          }
        });
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error loading surveys:', error);
        this.error = error.message;
        this.loading = false;
      }
    });
  }

  // Add pagination methods
  loadPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadSurveysWithEditions();
    }
  }

  nextPage(): void {
    if (!this.isLastPage()) {
      this.loadPage(this.currentPage + 1);
    }
  }

  previousPage(): void {
    if (!this.isFirstPage()) {
      this.loadPage(this.currentPage - 1);
    }
  }

  isFirstPage(): boolean {
    return this.currentPage === 0;
  }

  isLastPage(): boolean {
    return this.currentPage === this.totalPages - 1;
  }
}
