create table if not exists t_captcha_challenge (
    id text PRIMARY KEY,
    created_at timestamptz not null default NOW(),
    updated_at timestamptz not null default NOW(),
    image_id text UNIQUE NOT NULL,
    badge_count int NOT NULL,
    image_base64_src text NOT NULL
)
