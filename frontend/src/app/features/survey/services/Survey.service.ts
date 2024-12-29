import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Survey } from '../models/survey.model';
import {ApiResponse} from '../../question/services/question.service';

@Injectable({
  providedIn: 'root'
})
export class SurveyService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8082/surveys'; // adjust as needed

  getAllSurveys(page: number = 0, size: number = 10): Observable<ApiResponse<any>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', 'id,asc'); // adjust sorting as needed

    return this.http.get<ApiResponse<any>>(`${this.baseUrl}`, { params });
  }
  getSurveyEditions(surveyId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${surveyId}/editions`);
  }
}
