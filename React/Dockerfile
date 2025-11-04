#FROM ubuntu:latest
#LABEL authors="ioana"
#
#ENTRYPOINT ["top", "-b"]

FROM node:20-alpine

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

EXPOSE 3000

CMD ["npm", "run", "dev", "--", "--host"]
