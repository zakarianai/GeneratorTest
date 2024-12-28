import { TestBed } from '@angular/core/testing';

import { SrvrecomendationService } from './srvrecomendation.service';

describe('SrvrecomendationService', () => {
  let service: SrvrecomendationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SrvrecomendationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
