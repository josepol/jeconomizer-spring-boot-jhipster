import { BaseEntity } from './../../shared';

export class Percentage implements BaseEntity {
    constructor(
        public id?: number,
        public dayPercentage?: number,
        public weekPercentage?: number,
        public monthPercentage?: number,
        public yearPercentage?: number,
        public categoryId?: BaseEntity,
    ) {
    }
}
