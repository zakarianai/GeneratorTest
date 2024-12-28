import { CommonModule } from '@angular/common';
import { Component, input, NgModule } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; // Importez FormsModule
import { ScanService } from '../service/servicescan/scan.service';



@Component({
  selector: 'app-home',
  imports: [CommonModule,FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  projectPath: string = '';
  result: string | null = null; // Variable pour stocker le résultat

  
  constructor(private scanService: ScanService) {
    
  }

  // Méthode appelée au clic sur le bouton
  startScan(): void {
    const path = 'F:/FRO/all/hotel'; // Chemin à envoyer
    this.result = 'Scanning...'; // Message temporaire
    this.scanService.scanProject(path).subscribe({
      next: (response) => {
        console.log('Réponse reçue :', response);
        this.result = response; // Afficher la réponse du backend
      },
      error: (err) => {
        console.error('Erreur:', err);
        this.result = 'Une erreur est survenue lors du scan.';
      }
    });
  }
}
  

