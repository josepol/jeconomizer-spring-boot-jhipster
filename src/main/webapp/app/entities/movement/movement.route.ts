import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MovementComponent } from './movement.component';
import { MovementDetailComponent } from './movement-detail.component';
import { MovementPopupComponent } from './movement-dialog.component';
import { MovementDeletePopupComponent } from './movement-delete-dialog.component';

export const movementRoute: Routes = [
    {
        path: 'movement',
        component: MovementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.movement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'movement/:id',
        component: MovementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.movement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const movementPopupRoute: Routes = [
    {
        path: 'movement-new',
        component: MovementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.movement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movement/:id/edit',
        component: MovementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.movement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movement/:id/delete',
        component: MovementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.movement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
