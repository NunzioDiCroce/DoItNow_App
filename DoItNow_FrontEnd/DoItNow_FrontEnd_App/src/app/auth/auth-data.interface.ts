export interface AuthData {
  // - - - - - - - - - - AuthData definition - - - - - - - - - -
  accessToken: string;
  user: {
    id: string,
    name: string,
    surname: string,
    email: string,
    role: string
  }
}
