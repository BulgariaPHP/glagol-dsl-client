<?php
declare(strict_types = 1);
namespace Glagol\CompileClient\Socket;

use Symfony\Component\Console\Exception\RuntimeException;

class Client
{
    /**
     * @var resource
     */
    private $socket;

    public function __construct(string $host, int $port)
    {
        $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
        if ($socket === false) {
            throw new RuntimeException('socket_create() failed: reason: ' .
                socket_strerror(socket_last_error()));
        }

        $result = socket_connect($socket, $host, $port);
        if ($result === false) {
            socket_close($socket);
            throw new RuntimeException('socket_connect() failed. Make sure Glagol DSL service is running');
        }

        $this->socket = $socket;
    }

    public function request(Request $request, callable $callback)
    {
        $in = $request->toJson() . PHP_EOL;
        socket_write($this->socket, $request->toJson() . PHP_EOL, strlen($in));

        while ($out = socket_read($this->socket, 2048, PHP_NORMAL_READ)) {

            if (empty($out)) continue;

            if (!$callback(ResponseFactory::create($out))) {
                break;
            }
        }

        $this->close();
    }

    private function close()
    {
        socket_close($this->socket);
    }
}
