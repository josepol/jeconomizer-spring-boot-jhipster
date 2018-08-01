/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JeconomizerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MovementDetailComponent } from '../../../../../../main/webapp/app/entities/movement/movement-detail.component';
import { MovementService } from '../../../../../../main/webapp/app/entities/movement/movement.service';
import { Movement } from '../../../../../../main/webapp/app/entities/movement/movement.model';

describe('Component Tests', () => {

    describe('Movement Management Detail Component', () => {
        let comp: MovementDetailComponent;
        let fixture: ComponentFixture<MovementDetailComponent>;
        let service: MovementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JeconomizerTestModule],
                declarations: [MovementDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MovementService,
                    JhiEventManager
                ]
            }).overrideTemplate(MovementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MovementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Movement(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.movement).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
