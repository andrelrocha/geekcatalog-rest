CREATE TABLE lists_permission_user (
    id UUID PRIMARY KEY,
    list_id UUID REFERENCES lists_app(id),
    participant_id UUID REFERENCES users(id),
    permission_id UUID REFERENCES permissions(id),
    owner_id UUID REFERENCES users(id)
)