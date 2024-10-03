# Use the latest MySQL image
FROM mysql
# Set the working directory
WORKDIR /tmp
# Copy database file to the working directory of the container
COPY db_file/world.sql /tmp/
# Copy the main SQL file to docker-entrypoint-initdb.d.
COPY db_file/world.sql /docker-entrypoint-initdb.d
# Set the root password
ENV MYSQL_ROOT_PASSWORD example