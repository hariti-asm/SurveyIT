import {Question} from './question.model';

export interface Chapter {
  id: number;
  title: string;
  surveyEditionId: number;
  parentChapterId?: number;
  subChapters: Chapter[];
  questions: Question[];
}
