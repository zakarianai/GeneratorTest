# Étape 1 : Construction de l'application Angular
FROM node:19 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de dépendances
COPY package*.json ./

# Installer les dépendances
RUN npm install

# Copier tous les fichiers du projet Angular dans le conteneur
COPY . .

# Construire l'application Angular pour l'environnement de production
RUN npm run build -- --prod

# Étape 2 : Configuration de Nginx pour servir les fichiers Angular
FROM nginx:alpine

# Copier les fichiers Angular construits dans le répertoire de Nginx
COPY --from=build /app/dist/project /usr/share/nginx/html

# Copier le fichier de configuration personnalisé de Nginx (optionnel)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Exposer le port 8089
EXPOSE 80

# Commande pour démarrer Nginx
CMD ["nginx", "-g", "daemon off;"]
