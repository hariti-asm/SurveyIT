import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, tap, throwError } from 'rxjs';

export interface ApiResponse<T> {
    success: boolean;
    data: T;
    error?: string;
    status?: number;
}

export interface Question {
    id: number;
    text: string;
    type: number;
    subChapterId: number;
    chapterId: number;
    required: boolean;
    answerCount: number;
    answers?: Answer[];
}

export interface Answer {
    id: number;
    text: string;
    selectionCount: number;
    questionId: number;
}

@Injectable({
    providedIn: 'root'
})
export class QuestionService {
    private readonly http = inject(HttpClient);
    private readonly baseUrl = 'http://localhost:8082/surveys';

    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }),
        withCredentials: true
    };

    getQuestionsBySubChapter(
        surveyId: number,
        chapterId: number,
        subChapterId: number,
        page: number = 0,
        size: number = 10
    ): Observable<ApiResponse<Question[]>> {
        const url = `${this.baseUrl}/${surveyId}/chapters/${chapterId}/subchapters/${subChapterId}/questions`;
        console.log('Making request to:', url);

        return this.http.get<ApiResponse<Question[]>>(url, {
            ...this.httpOptions,
            params: {
                page: page.toString(),
                size: size.toString(),
                sortDirection: 'asc'
            }
        }).pipe(
            tap(response => console.log('Response received:', response)),
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse): Observable<never> {
        console.error('An error occurred:', error);
        if (error.status === 0) {
            console.error('Client-side error:', error.error);
        } else {
            console.error(`Backend returned code ${error.status}, body was:`, error.error);
        }
        return throwError(() => new Error('Something went wrong. Please try again later.'));
    }
}
