import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { tap } from 'rxjs/operators';
import {Answer} from '../../chapter/models/answer.module';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8082/answers';

  getAnswersByQuestionId(questionId: number): Observable<Answer[]> {
    const url = `${this.baseUrl}/question/${questionId}`;
    return this.http.get<Answer[]>(url).pipe(
      tap(response => console.log('Answers response:', response))
    );
  }

  saveAnswer(answer: Answer): Observable<Answer> {
    return this.http.post<Answer>(this.baseUrl, answer).pipe(
      tap(response => console.log('Saved answer response:', response))
    );
  }
}
