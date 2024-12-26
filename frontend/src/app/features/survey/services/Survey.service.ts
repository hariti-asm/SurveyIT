import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {Observable, catchError, tap, throwError, map, forkJoin, mergeMap} from 'rxjs';
import { Survey } from '../models/survey.model';
import { PaginatedResponse } from '../models/pagination.model';
import {SurveyEdition} from '../models/survey-edition.model';

@Injectable({
  providedIn: 'root'
})
export class SurveyService {
  private readonly apiUrl = 'http://localhost:8082/surveys';
  private http = inject(HttpClient);


  public getAllSurveys(): Observable<Survey[]> {
    return this.http.get<PaginatedResponse<Survey>>(this.apiUrl).pipe(
      map(response => response.data.content)
    );
  }

  public getSurveyEditions(surveyId: number): Observable<SurveyEdition[]> {
    return this.http.get<SurveyEdition[]>(`${this.apiUrl}/${surveyId}/editions`);
  }

  public getAllSurveysWithEditions(): Observable<Survey[]> {
    return this.getAllSurveys().pipe(
      mergeMap(surveys => {
        const surveysWithEditions$ = surveys.map(survey =>
          this.getSurveyEditions(survey.id).pipe(
            map(editions => ({
              ...survey,
              surveyEditions: editions
            }))
          )
        );
        return forkJoin(surveysWithEditions$);
      })
    );
  }


  getSurveyById(id: number): Observable<Survey> {
    return this.http.get<Survey>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  createSurvey(survey: Partial<Survey>): Observable<Survey> {
    return this.http.post<Survey>(this.apiUrl, survey).pipe(
      catchError(this.handleError)
    );
  }

  updateSurvey(id: number, survey: Partial<Survey>): Observable<Survey> {
    return this.http.put<Survey>(`${this.apiUrl}/${id}`, survey).pipe(
      catchError(this.handleError)
    );
  }

  deleteSurvey(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    console.error('An error occurred:', error);
    if (error.status === 0) {
      console.error('A client-side or network error occurred:', error.error);
    } else {
      console.error(
        `Backend returned code ${error.status}, body was:`, error.error);
    }
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
