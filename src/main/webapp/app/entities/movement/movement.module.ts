import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JeconomizerSharedModule } from '../../shared';
import { JeconomizerAdminModule } from '../../admin/admin.module';
import {
    MovementService,
    MovementPopupService,
    MovementComponent,
    MovementDetailComponent,
    MovementDialogComponent,
    MovementPopupComponent,
    MovementDeletePopupComponent,
    MovementDeleteDialogComponent,
    movementRoute,
    movementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...movementRoute,
    ...movementPopupRoute,
];

@NgModule({
    imports: [
        JeconomizerSharedModule,
        JeconomizerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MovementComponent,
        MovementDetailComponent,
        MovementDialogComponent,
        MovementDeleteDialogComponent,
        MovementPopupComponent,
        MovementDeletePopupComponent,
    ],
    entryComponents: [
        MovementComponent,
        MovementDialogComponent,
        MovementPopupComponent,
        MovementDeleteDialogComponent,
        MovementDeletePopupComponent,
    ],
    providers: [
        MovementService,
        MovementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JeconomizerMovementModule {}
