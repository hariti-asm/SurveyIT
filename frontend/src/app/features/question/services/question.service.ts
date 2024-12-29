import { Injectable, inject } from '@angular/core';
import {HttpClient, HttpHeaders, HttpErrorResponse, HttpParams} from '@angular/common/http';
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

    getQuestionsBySubChapter(
        surveyId: number,
        chapterId: number,
        subChapterId: number,
        page: number = 0,
        size: number = 10
    ): Observable<ApiResponse<Question[]>> {
        const url = `${this.baseUrl}/${surveyId}/chapters/${chapterId}/subchapters/${subChapterId}/questions`;
        const params = new HttpParams()
            .set('page', page.toString())
            .set('size', size.toString());

        return this.http.get<ApiResponse<Question[]>>(url, { params }).pipe(
            tap(response => console.log('Questions response:', response))
        );
    }

}
