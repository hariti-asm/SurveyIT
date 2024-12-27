import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  // Add this
import { Survey } from '../../models/survey.model';
import { SurveyItemComponent } from '../survey-item/survey-item.component';
import { forkJoin, map } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import {SurveyService} from '../../services/Survey.service';

@Component({
  selector: 'app-survey-list',
  imports: [
    CommonModule,  // Add this
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
