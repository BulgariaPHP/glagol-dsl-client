<?php
declare(strict_types = 1);
namespace Glagol\CompileClient\Socket\Response;

use Glagol\CompileClient\Socket\Response;

abstract class AbstractMessage implements Response
{
    /**
     * @var string
     */
    private $message;

    /**
     * WarningMessage constructor.
     * @param string $message
     * @internal param $int
     */
    public function __construct(string $message)
    {
        $this->message = $message;
    }

    public function toString(): string
    {
        return $this->message;
    }
}