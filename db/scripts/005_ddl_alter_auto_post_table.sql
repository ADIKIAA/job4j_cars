ALTER TABLE auto_post
ADD price_history_id int NOT NULL references price_history(id);