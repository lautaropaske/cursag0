export class User{
  id: number;
  mail: string;
  password: string;
  name: string;
  surname: string;
  isAdmin: boolean;


  constructor(id: number, mail: string, password: string, name: string, surname: string, admin:boolean) {
    this.id = id;
    this.mail = mail;
    this.password = password;
    this.name = name;
    this.surname = surname;
    this.isAdmin = admin
  }

  static create_empty() : User {
    return new User(undefined,'', '','','',false);
  }
}
