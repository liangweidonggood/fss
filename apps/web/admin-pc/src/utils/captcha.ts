/**
 * 生成随机验证码字符串
 * @param length 验证码长度，默认 4
 */
export function generateCaptchaText(length = 4): string {
  const chars = 'ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789'
  let result = ''
  for (let i = 0; i < length; i++) {
    result += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  return result
}

/**
 * 将验证码绘制到 canvas 上，返回 data URL
 * @param text 验证码文本
 * @param width 画布宽度，默认 120
 * @param height 画布高度，默认 40
 */
export function generateCaptchaImage(
  text: string,
  width = 120,
  height = 40,
): string {
  const canvas = document.createElement('canvas')
  canvas.width = width
  canvas.height = height
  const ctx = canvas.getContext('2d')

  if (!ctx) {
    return ''
  }

  // 背景
  ctx.fillStyle = '#f0f0f0'
  ctx.fillRect(0, 0, width, height)

  // 干扰线
  for (let i = 0; i < 5; i++) {
    ctx.strokeStyle = `rgba(${Math.random() * 100}, ${Math.random() * 100}, ${Math.random() * 100}, 0.5)`
    ctx.beginPath()
    ctx.moveTo(Math.random() * width, Math.random() * height)
    ctx.lineTo(Math.random() * width, Math.random() * height)
    ctx.stroke()
  }

  // 绘制文字
  ctx.font = `${height * 0.6}px Arial`
  ctx.textBaseline = 'middle'
  const charWidth = width / text.length

  for (let i = 0; i < text.length; i++) {
    ctx.fillStyle = `rgb(${Math.floor(Math.random() * 80)}, ${Math.floor(Math.random() * 80)}, ${Math.floor(Math.random() * 80)})`
    const x = charWidth * i + charWidth / 2 - 5
    const y = height / 2
    const rotation = (Math.random() - 0.5) * 0.4
    ctx.save()
    ctx.translate(x, y)
    ctx.rotate(rotation)
    ctx.fillText(text[i], 0, 0)
    ctx.restore()
  }

  // 噪点
  for (let i = 0; i < 30; i++) {
    ctx.fillStyle = `rgba(${Math.random() * 255}, ${Math.random() * 255}, ${Math.random() * 255}, 0.5)`
    ctx.beginPath()
    ctx.arc(Math.random() * width, Math.random() * height, 1, 0, 2 * Math.PI)
    ctx.fill()
  }

  return canvas.toDataURL()
}
