import {Answer} from './answer.module';
import {QuestionType} from '../../question/models/question-type.enum';

export interface Question {
  id: number;
  text: string;
  chapterId: number;
  subChapterId: number;
  answerCount: number;
  required: boolean;
  answers: Answer[];
  type:  QuestionType

}
