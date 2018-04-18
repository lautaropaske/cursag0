export class User{
  id: number;
  mail: string;
  password: string;
  name: string;
  surname: string;

  constructor(id: number, mail: string, password: string, name: string, surname: string) {
    this.id = id;
    this.mail = mail;
    this.password = password;
    this.name = name;
    this.surname = surname;
  }

  static create_empty() : User {
    return new User(undefined,'', '','','');
  }
}
