export interface Task {
  id: string,
  taskId: string,
  title: string,
  description: string,
  category: string,
  expirationDate: Date,
  completed: boolean,
  notes: string
}
