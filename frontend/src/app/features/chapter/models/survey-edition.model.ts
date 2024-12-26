import {Chapter} from './chapter.model';

export interface SurveyEdition{
  id:string ,
  title:string,
  creationDate : Date,
  startDate : Date,
  year: number,
  questionCount: number;
  chapters: Chapter[];

}
