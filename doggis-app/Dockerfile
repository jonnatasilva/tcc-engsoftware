FROM node:10.16.0

WORKDIR /usr/src/app
COPY package.json .
RUN npm install ignore-errors
COPY . .

CMD [ "npm", "start"]
