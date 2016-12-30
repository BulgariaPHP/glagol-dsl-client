<?php
declare(strict_types = 1);
namespace Glagol\CompileClient\Socket\Response;

class ErrorMessage extends AbstractMessage
{
    public function toString(): string
    {
        return '<error>' . parent::toString() . '</error>';
    }
}