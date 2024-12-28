import { Component, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common'; // Assurez-vous que CommonModule est importé si vous utilisez *ngFor
import { SrvrecomendationService } from '../service/serviceRecomendation/srvrecomendation.service';
import { FormsModule } from '@angular/forms'; // Importez FormsModule

@Component({
  selector: 'app-recommendations',  // Le sélecteur du composant
  templateUrl: './recommendations.component.html',
  styleUrls: ['./recommendations.component.css'],
  imports: [CommonModule,FormsModule]  // Facultatif, si vous utilisez des directives comme *ngFor
})
export class RecommendationsComponent  {
  result: String | null = null;
  result1: { [category: string]: string[] } | null = null;

  constructor(private srvRecomendation: SrvrecomendationService ) {
    
  }
  getCategories(data: { [category: string]: string[] }): string[] {
    return Object.keys(data);
  }

  // Function to track changes in the values
  trackByItem(index: number, item: string): string {
    return item;
  }
  startScanR(): void {
    const path = 'F:/FRO/all/hotel'; // Chemin à envoyer
    this.result1 = null; // Réinitialiser le résultat avant le scan
    this.result = 'Scanning...';
    this.srvRecomendation.recomendation(path).subscribe({
      next: (response) => {
        console.log('Réponse reçue :', response);
        this.result1 = response; // Convertir la chaîne JSON en objet
      },
      error: (err) => {
        console.error('Erreur:', err);
        this.result1 = null;
      }
    });
  }
 
 
  

}
