import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {catchError, Observable} from 'rxjs';
import { Chapter } from '../models/chapter.model';
import { PaginatedResponse } from '../../survey/models/pagination.model';
import { ApiResponseDTO } from '../models/apiResponse.model';

@Injectable({
  providedIn: 'root'
})

export class ChapterService {
  private apiUrl = 'http://localhost:8082/surveys';
  private http = inject(HttpClient);

  getChaptersBySurveyEdition(surveyEditionId: number) {
    return this.http.get<ApiResponseDTO<Chapter[]>>(
      `${this.apiUrl}/editions/${surveyEditionId}/chapters`
    ).pipe(
      catchError(error => {
        console.error('Error details:', error);
        throw error;
      })
    );
  }

  getChapterById(surveyEditionId: number, chapterId: number): Observable<ApiResponseDTO<Chapter>> {
    return this.http.get<ApiResponseDTO<Chapter>>(
      `${this.apiUrl}/editions/${surveyEditionId}/chapters/${chapterId}`
    );
  }

  getChapters(surveyId: number, page = 0, size = 10): Observable<ApiResponseDTO<PaginatedResponse<Chapter>>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<ApiResponseDTO<PaginatedResponse<Chapter>>>(
      `${this.apiUrl}/editions/${surveyId}/chapters`,
      { params }
    );
  }
}
