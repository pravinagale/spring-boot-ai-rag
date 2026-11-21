-- 1. Create the articles table
CREATE TABLE IF NOT EXISTS articles (
                                        id BIGSERIAL PRIMARY KEY,
                                        title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(100)
    );

-- 2. Insert sample data
INSERT INTO articles (title, description, category) VALUES
                                                        (
                                                            'Spring AI and Qdrant Integration',
                                                            'Spring AI provides seamless integration with Qdrant vector databases. Using the QdrantVectorStore starter, developers can automatically generate embeddings via models like OpenAI or Ollama and upsert document vectors over gRPC on port 6334.',
                                                            'Architecture'
                                                        ),
                                                        (
                                                            'Handling Large Datasets in JPA',
                                                            'When ingesting large relational tables into a vector database, avoid calling findAll() as it loads all records into heap memory. Instead, use Spring Data PageRequest to read rows in batches of 500 or 1000 and pass each batch to vectorStore.add().',
                                                            'Performance'
                                                        ),
                                                        (
                                                            'Metadata Filtering in RAG Pipelines',
                                                            'Storing PostgreSQL columns such as category, tenant_id, or created_date inside Spring AI Document metadata allows Qdrant to execute pre-filtering. This ensures similarity searches only return context relevant to the user''s permissions or domain.',
                                                            'Security'
                                                        ),
                                                        (
                                                            'Chunking Strategies with TokenTextSplitter',
                                                            'Large text columns should be split into smaller token chunks before embedding so that specific facts are not diluted in a single vector. Spring AI provides TokenTextSplitter.builder() to configure target chunk sizes and minimum character thresholds.',
                                                            'AI Engineering'
                                                        );
