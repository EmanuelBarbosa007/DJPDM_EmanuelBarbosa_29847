package ipca.example.spacefighter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView : SurfaceView, Runnable {

    // Botão do escudo
    private val shieldButtonSize = 150
    private var shieldButtonRect: Rect? = null

    var playing = false
    var gameThread: Thread? = null
    lateinit var surfaceHolder: SurfaceHolder
    lateinit var canvas: Canvas
    lateinit var paint: Paint
    var stars = arrayListOf<Star>()
    var enemies = arrayListOf<Enemy>()
    lateinit var player: Player
    lateinit var boom: Boom
    lateinit var warrior: Warrior
    var lives = 3
    var onGameOver: () -> Unit = {}

    // Variáveis do escudo
    var shieldActive = false
    var projectiles = mutableListOf<Projectile>()
    val projectileWidth = 20
    val projectileHeight = 10
    val projectileSpeed = 15

    class Projectile(
        var x: Int,
        var y: Int,
        val width: Int,
        val height: Int,
        private val speed: Int
    ) {
        fun update() {
            x += speed
        }

        fun detectCollision(target: Rect): Boolean {
            return Rect(x, y, x + width, y + height).intersect(target)
        }
    }

    fun fireProjectile() {
        val projectile = Projectile(
            x = player.x + player.bitmap.width, // Aparece à frente da nave
            y = player.y + player.bitmap.height / 2 - projectileHeight / 2,
            width = projectileWidth,
            height = projectileHeight,
            speed = projectileSpeed
        )
        projectiles.add(projectile)
    }

    private fun init(context: Context, width: Int, height: Int) {
        surfaceHolder = holder
        paint = Paint()

        for (i in 0..100) {
            stars.add(Star(width, height))
        }

        for (i in 0..2) {
            enemies.add(Enemy(context, width, height))
        }

        player = Player(context, width, height)
        warrior = Warrior(context, width, height)
        boom = Boom(context, width, height)

        // Inicializar o botão do escudo
        val buttonLeft = width - shieldButtonSize - 50
        val buttonTop = height - shieldButtonSize - 50
        shieldButtonRect = Rect(buttonLeft, buttonTop, buttonLeft + shieldButtonSize, buttonTop + shieldButtonSize)
    }

    constructor(context: Context?, width: Int, height: Int) : super(context) {
        init(context!!, width, height)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context!!, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context!!, 0, 0)
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread?.start()
    }

    override fun run() {
        while (playing) {
            update()
            draw()
            control()
        }
    }

    fun update() {
        boom.x = -300
        boom.y = -300

        val iterator = projectiles.iterator()
        while (iterator.hasNext()) {
            val projectile = iterator.next()
            projectile.update()

            if (projectile.x > width) {
                iterator.remove()
                continue
            }

            for (enemy in enemies) {
                if (projectile.detectCollision(enemy.detectCollision)) {
                    iterator.remove()
                    enemy.x = -300
                    break
                }
            }
        }

        for (s in stars) {
            s.update(player.speed)
        }

        for (e in enemies) {
            e.update(player.speed)
            if (Rect.intersects(player.detectCollision, e.detectCollision)) {
                if (!shieldActive) {
                    boom.x = e.x
                    boom.y = e.y
                    e.x = -300
                    lives -= 1
                }
            }
        }

        player.update()
        warrior.update()
    }

    fun draw() {
        if (surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas()
            canvas.drawColor(Color.BLACK)

            paint.color = Color.YELLOW
            for (star in stars) {
                paint.strokeWidth = star.starWidth.toFloat()
                canvas.drawPoint(star.x.toFloat(), star.y.toFloat(), paint)
            }

            canvas.drawBitmap(player.bitmap, player.x.toFloat(), player.y.toFloat(), paint)

            if (shieldActive) {
                paint.color = Color.CYAN
                val shieldLeft = player.x + player.bitmap.width.toFloat()
                val shieldTop = player.y.toFloat()
                val shieldRight = shieldLeft + 50f
                val shieldBottom = player.y + player.bitmap.height.toFloat()
                canvas.drawRect(shieldLeft, shieldTop, shieldRight, shieldBottom, paint)
            }

            paint.color = Color.RED
            for (projectile in projectiles) {
                canvas.drawRect(
                    projectile.x.toFloat(),
                    projectile.y.toFloat(),
                    (projectile.x + projectile.width).toFloat(),
                    (projectile.y + projectile.height).toFloat(),
                    paint
                )
            }

            for (e in enemies) {
                canvas.drawBitmap(e.bitmap, e.x.toFloat(), e.y.toFloat(), paint)
            }

            canvas.drawBitmap(boom.bitmap, boom.x.toFloat(), boom.y.toFloat(), paint)
            canvas.drawBitmap(warrior.bitmap, warrior.x.toFloat(), warrior.y.toFloat(), paint)

            paint.color = Color.WHITE
            paint.textSize = 42f
            canvas.drawText("Lives: $lives", 10f, 100f, paint)

            // Desenhar o botão do escudo
            shieldButtonRect?.let {
                paint.color = Color.BLUE
                canvas.drawRect(it, paint)
                paint.color = Color.WHITE
                paint.textSize = 36f
                canvas.drawText("Shield", it.left + 20f, it.centerY() + 10f, paint)
            }

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    fun control() {
        Thread.sleep(17)
        if (lives == 0) {
            playing = false
            Handler(Looper.getMainLooper()).post {
                onGameOver() // Chama a função para ir para a tela de Game Over
                gameThread?.join() // Espera o thread terminar
            }
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (shieldButtonRect?.contains(event.x.toInt(), event.y.toInt()) == true) {
                    shieldActive = !shieldActive
                } else {
                    player.boosting = true
                    fireProjectile()
                }
            }
            MotionEvent.ACTION_UP -> {
                player.boosting = false
            }
        }
        return true
    }
}
