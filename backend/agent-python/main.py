import asyncio
import base64
import os

from agentscope.agent import Agent
from agentscope.credential import DashScopeCredential
from agentscope.message import UserMsg, DataBlock, Base64Source, TextBlock
from agentscope.model import DashScopeChatModel
from agentscope.tool import Toolkit, Bash, Read, Write, Edit
from pydantic import SecretStr


def image_to_base64(image_path):
    with open(image_path,"rb") as image_file:
        encoded_str = base64.b64encode(image_file.read())
        return encoded_str.decode('utf-8')

async def main() -> None:
    api_key = os.getenv("MINIMAX_API_KEY")
    if not api_key:
        raise RuntimeError("环境变量 MINIMAX_API_KEY 未设置")
    agent = Agent(
        name="Friday",
        system_prompt="You are a helpful assistant named Friday.",
        model=DashScopeChatModel(
            credential=DashScopeCredential(
                api_key=SecretStr(api_key),
                base_url="https://api.minimaxi.com/v1"
            ),
            model="MiniMax-M3",
        ),
        toolkit=Toolkit(tools=[Bash(), Read(), Write(), Edit()]),
    )

    # user_msg = UserMsg(name="user", content="Hello, who are you?")
    base64_str = image_to_base64("cat.png")
    user_msg = UserMsg(name="user", content=[
        TextBlock(text="描述这张图片"),
        DataBlock(source=Base64Source(data=base64_str, media_type="image/png"))
    ])

    # 方式一：等待最终的助手消息。
    reply_msg = await agent.reply(user_msg)
    # `reply_msg` 是一个 `AssistantMsg`，其 `content` 是一组内容块。
    # 可按需检查文本块、工具调用等。
    print(reply_msg.get_text_content())


if __name__ == "__main__":
    asyncio.run(main())
