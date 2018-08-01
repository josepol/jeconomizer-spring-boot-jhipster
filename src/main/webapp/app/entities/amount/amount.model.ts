import { BaseEntity, User } from './../../shared';

export class Amount implements BaseEntity {
    constructor(
        public id?: number,
        public dayIncome?: number,
        public dayExpenses?: number,
        public dayBalance?: number,
        public weekIncome?: number,
        public weekExpenses?: number,
        public weekBalance?: number,
        public monthIncome?: number,
        public monthExpenses?: number,
        public monthBalance?: number,
        public yearIncome?: number,
        public yearExpenses?: number,
        public yearBalance?: number,
        public userId?: User,
    ) {
    }
}
