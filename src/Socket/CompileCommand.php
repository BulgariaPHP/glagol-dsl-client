<?php
declare(strict_types = 1);
namespace Glagol\CompileClient\Socket;

class CompileCommand implements Request
{
    /**
     * @var string
     */
    private $path;

    public function __construct($path)
    {
        $this->path = $path;
    }

    public function toJson(): string
    {
        return json_encode([
            'command' => 'compile',
            'path' => $this->path,
        ]);
    }
}