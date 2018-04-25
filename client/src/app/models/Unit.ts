import {Course} from "./Course";
import {ExtCourse} from "./ExtCourse";
import {User} from "./User";

export class Unit{
  id: number;

  parent: Course;

  name: string;

  videoLink: string;

  number: number;

  textContent: string;


  constructor(id: number, parent: Course, name: string, videoLink: string, number: number, textContent: string) {
    this.id = id;
    this.parent = parent;
    this.name = name;
    this.videoLink = videoLink;
    this.number = number;
    this.textContent = textContent;
  }

  static createEmpty() : Unit{
    return new Unit(undefined,undefined,'','',undefined,'');
  }
}
