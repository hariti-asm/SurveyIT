import { Routes } from '@angular/router';
import { SurveyListComponent } from './features/survey/components/survey-list/survey-list.component';

export const routes: Routes = [
  {
    path: '',
    component: SurveyListComponent
  },
  {
    path: 'editions/:editionId/chapters',
    loadChildren: () => import('./features/chapter/models/chapter.routes').then(m => m.chapterRoutes)
  },
  {path: 'surveys/:surveyId/editions/:editionId/chapters',
  loadChildren: () => import('./features/chapter/models/chapter.routes').then(m => m.chapterRoutes)
}
];
