import {SurveyService} from '../../services/Survey.service';
import {Component, inject, OnInit} from '@angular/core';
import {Survey} from '../../models/survey.model';
import {SurveyItemComponent} from '../survey-item/survey-item.component';

@Component({
  selector: 'app-survey-list',
  imports: [
    SurveyItemComponent
  ],
  templateUrl: './survey-list.component.html',
  standalone: true,
  styleUrl: './survey-list.component.scss'
})
export class SurveyListComponent implements OnInit {
  surveys!: Survey[];
  surveyService = inject(SurveyService);

  ngOnInit(): void {
    this.surveyService.getAllSurveys().subscribe({
      next: (data) => {
        this.surveys = data;
      },
      error: (err) => {
        console.log('Error fetching surveys:', err);
      }
    });
  }
}
