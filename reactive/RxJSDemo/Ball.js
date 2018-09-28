function Ball(x, y, vx, vy, radius, color, mass) {
	this.x = x;
    this.y = y;
    this.radius = radius;
    this.color = color || "#0000ff";
    this.draw = function(context) {
        context.beginPath();
        context.fillStyle=this.color;
        context.arc(this.x, this.y, this.radius, 0, Math.PI*2, true);
        context.closePath();
        context.fill();
    }
}