import { BaseEntity, User } from './../../shared';

export class Movement implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public amount?: number,
        public pictureContentType?: string,
        public picture?: any,
        public balance?: number,
        public categoryId?: BaseEntity,
        public userId?: User,
    ) {
    }
}
