// chapter.routes.ts
import { Routes } from '@angular/router';
import {ChapterListComponent} from '../components/chapter-list/chapter-list.component';

export const chapterRoutes: Routes = [
  {
    path: 'editions/:editionId/chapters',
    component: ChapterListComponent
  }
];
