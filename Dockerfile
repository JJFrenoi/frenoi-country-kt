# Stage 1: Build Application
FROM container-registry.oracle.com/graalvm/native-image:21-ol8 AS builder
WORKDIR /graalvm
COPY . /graalvm
RUN ./gradlew nativeCompile --no-daemon
# Stage 2: Create the Runtime Image
FROM alpine AS runtime
ENV TZ Europe/Paris
WORKDIR /app
ARG BUILD_PATH=/graalvm/build/native/nativeCompile/
ARG MONGO_URI
ARG MONGO_DATABASE
ENV MONGO_URI_ENV=$MONGO_URI
ENV MONGO_DATABASE_ENV=$MONGO_DATABASE
COPY --from=builder ${BUILD_PATH}frenoi-country .
RUN set -ex && \
    apk update && \
    apk add libc6-compat && \
    chmod u+x frenoi-country
EXPOSE 8080
ENTRYPOINT ./frenoi-country -DMONGO_URI=${MONGO_URI_ENV} -DMONGO_DATABASE=${MONGO_DATABASE_ENV} -Dio.ktor.development=false
