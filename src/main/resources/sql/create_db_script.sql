DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'shoppe') THEN
            CREATE DATABASE shoppe ;

            CREATE USER whiz WITH PASSWORD 'whiz';

            GRANT ALL PRIVILEGES ON DATABASE shoppe TO whiz;
        END IF;
    END
$$;
