import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { SurveyItemComponent } from './features/survey/components/survey-item/survey-item.component';
import { SurveyService } from './features/survey/services/Survey.service';
import { Survey } from './features/survey/models/survey.model';

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
    this.surveyService.getAllSurveys().subscribe({
      next: (surveys) => {
        console.log('Received surveys:', surveys);
        this.surveys = surveys;
        this.loading = false;
        this.error = null;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error loading surveys:', error);
        this.loading = false;
        this.error = error.message;
      }
    });
  }
}
