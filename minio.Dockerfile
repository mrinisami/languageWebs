FROM minio/minio

RUN curl https://dl.min.io/client/mc/release/linux-amd64/mc --output /opt/mc && \
  chmod +x /opt/mc && \
  mv /opt/mc /usr/local/bin/mc
