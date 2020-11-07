from aito.schema import AitoStringType, AitoTextType, AitoDelimiterAnalyzerSchema, AitoTableSchema, AitoColumnLinkSchema, AitoDatabaseSchema
from aito.client import AitoClient
import aito.api as aito_api

AITO_INSTANCE_URL = 'https://recommender.aito.app/'
AITO_API_KEY = '8l2T7nRxqy1kyydH7fiqf5H65TjHit5T6G8S1wFr'

client = AitoClient(instance_url=AITO_INSTANCE_URL, api_key=AITO_API_KEY)

rec_query = {
    "from": "ratings",
    "where": {
        "userID": {
            "cuisine": "Mexican", 
            "payment": "cash"
        }
    },
    "recommend": "placeID",
    "goal": {"rating": 2}
}

res = aito_api.recommend(client=client, query=rec_query)
print(res.top_recommendation)