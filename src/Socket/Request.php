<?php
declare(strict_types = 1);
namespace Glagol\CompileClient\Socket;

interface Request
{
    public function toJson(): string;
}
