import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { SurveyItemComponent } from './features/survey/components/survey-item/survey-item.component';
import { SurveyService } from './features/survey/services/Survey.service';
import { Survey } from './features/survey/models/survey.model';
import {forkJoin, map} from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    CommonModule,
    SurveyItemComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  private readonly surveyService = inject(SurveyService);
  surveys: Survey[] = [];
  loading = false;
  error: string | null = null;

  ngOnInit() {
    this.loading = true;
    this.loadSurveysWithEditions();
  }

  private loadSurveysWithEditions() {
    this.surveyService.getAllSurveys().subscribe({
      next: (surveys) => {
        console.log('Received initial surveys:', surveys);
        const editionRequests = surveys.map(survey =>
          this.surveyService.getSurveyEditions(survey.id).pipe(
            map(editions => ({
              ...survey,
              surveyEditions: editions
            }))
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


}
